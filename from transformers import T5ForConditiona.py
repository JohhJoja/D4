import tensorflow as tf
from tensorflow import keras
from tensorflow.keras.layers import Embedding, LSTM, Dense, Bidirectional, Attention, Concatenate, Input
import numpy as np
import json
import pickle

# Загружаем обучающие данные
with open("E:\Java\deeplomka\JDBC\dataset.json", "r", encoding="utf-8") as f:
    dataset = json.load(f)

texts = [item["text"] for item in dataset]
sqls = [item["sql"] for item in dataset]

# Токенизация входных данных
text_tokenizer = keras.preprocessing.text.Tokenizer()
text_tokenizer.fit_on_texts(texts)
X = text_tokenizer.texts_to_sequences(texts)
X = keras.preprocessing.sequence.pad_sequences(X, maxlen=15)

# Токенизация выходных данных
sql_tokenizer = keras.preprocessing.text.Tokenizer()
sql_tokenizer.fit_on_texts(sqls)
y = sql_tokenizer.texts_to_sequences(sqls)
y = keras.preprocessing.sequence.pad_sequences(y, maxlen=15)

# Размеры словарей
text_vocab_size = len(text_tokenizer.word_index) + 1
sql_vocab_size = len(sql_tokenizer.word_index) + 1

# Создаем модель
class SQLGenerator(keras.Model):
    def __init__(self, text_vocab_size, sql_vocab_size, embed_dim=128, lstm_units=128):
        super(SQLGenerator, self).__init__()
        self.embedding = Embedding(input_dim=text_vocab_size, output_dim=embed_dim, mask_zero=True)
        self.encoder = Bidirectional(LSTM(lstm_units, return_sequences=True, return_state=True))
        self.attention = Attention(use_scale=True)  # Используем масштабирование для внимания
        self.decoder_lstm = LSTM(lstm_units * 2, return_sequences=True, return_state=True)
        self.dense = Dense(sql_vocab_size, activation="softmax")

    def call(self, inputs):
        x = self.embedding(inputs)
        encoder_output, forward_h, forward_c, backward_h, backward_c = self.encoder(x)
        hidden_state = Concatenate()([forward_h, backward_h])
        cell_state = Concatenate()([forward_c, backward_c])
        
        # Преобразование перед входом в attention
        context_vector = self.attention([encoder_output, encoder_output])  # query и key одинаковые
        context_vector = tf.reduce_mean(context_vector, axis=1)  # Для упрощения можно взять среднее значение по оси 1
        
        # Преобразуем в 3D для LSTM
        context_vector = tf.expand_dims(context_vector, axis=1)  # Добавляем дополнительную ось для временных шагов
        
        # Применяем декодер LSTM
        decoder_output, _, _ = self.decoder_lstm(context_vector, initial_state=[hidden_state, cell_state])
        output = self.dense(decoder_output)
        return output

# Инициализация модели
model = SQLGenerator(text_vocab_size, sql_vocab_size)
model.compile(optimizer="adam", loss="sparse_categorical_crossentropy", metrics=["accuracy"])

# Преобразуем выходные данные в одномерные метки для sparse_categorical_crossentropy
y = np.expand_dims(y, -1)

# Обучение
model.fit(X, y, epochs=50, batch_size=32)

# Сохраняем модель и токенизаторы
model.save("sql_generator.h5")
with open("text_tokenizer.pkl", "wb") as f:
    pickle.dump(text_tokenizer, f)
with open("sql_tokenizer.pkl", "wb") as f:
    pickle.dump(sql_tokenizer, f)
