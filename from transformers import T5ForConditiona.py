import tensorflow as tf
from tensorflow import keras
from tensorflow.keras.layers import Embedding, LSTM, Dense, Bidirectional, Attention
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
    def __init__(self, text_vocab_size, sql_vocab_size):
        super(SQLGenerator, self).__init__()
        self.embedding = Embedding(input_dim=text_vocab_size, output_dim=128, input_length=30)
        self.encoder = Bidirectional(LSTM(128, return_sequences=True, return_state=True))
        self.attention = Attention()
        self.decoder = LSTM(128, return_sequences=True, return_state=True)
        self.dense = Dense(sql_vocab_size, activation="softmax")

    def call(self, inputs):
        x = self.embedding(inputs)
        encoder_output, forward_h, forward_c, backward_h, backward_c = self.encoder(x)
        decoder_input = encoder_output  # Начинаем декодирование
        context_vector = self.attention([decoder_input, encoder_output])
        decoder_output, _, _ = self.decoder(context_vector)
        output = self.dense(decoder_output)
        return output

# Инициализация модели
model = SQLGenerator(text_vocab_size, sql_vocab_size)
model.compile(optimizer="adam", loss="categorical_crossentropy", metrics=["accuracy"])

# One-hot encoding для выхода
y_one_hot = keras.utils.to_categorical(y, num_classes=sql_vocab_size)

# Обучение
model.fit(X, y_one_hot, epochs=50, batch_size=32)

# Сохраняем модель и токенизаторы
model.save("sql_generator.h5")
with open("text_tokenizer.pkl", "wb") as f:
    pickle.dump(text_tokenizer, f)
with open("sql_tokenizer.pkl", "wb") as f:
    pickle.dump(sql_tokenizer, f)
