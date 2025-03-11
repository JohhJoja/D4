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

# Преобразуем y в one-hot формат
y = keras.utils.to_categorical(y, num_classes=sql_vocab_size)  # (batch_size, 15, sql_vocab_size)

# Создаем модель
class SQLGenerator(keras.Model):
    def __init__(self, text_vocab_size, sql_vocab_size, embed_dim=128, lstm_units=128):
        super(SQLGenerator, self).__init__()
        self.embedding = Embedding(input_dim=text_vocab_size, output_dim=embed_dim, mask_zero=True)
        self.encoder = Bidirectional(LSTM(lstm_units, return_sequences=True, return_state=True))
        self.attention = Attention(use_scale=True)
        self.decoder_lstm = LSTM(lstm_units * 2, return_sequences=True, return_state=True)  
        self.dense = Dense(sql_vocab_size, activation="softmax")

    def call(self, inputs):
        encoder_inputs, decoder_inputs = inputs  # Теперь два входа

        # **Энкодер**
        x = self.embedding(encoder_inputs)
        encoder_output, forward_h, forward_c, backward_h, backward_c = self.encoder(x)
        hidden_state = Concatenate()([forward_h, backward_h])
        cell_state = Concatenate()([forward_c, backward_c])

        # **Attention**
        context_vector = self.attention([encoder_output, encoder_output])
        context_vector = tf.reduce_mean(context_vector, axis=1)
        context_vector = tf.expand_dims(context_vector, axis=1)

        # **Декодер**
        decoder_embedded = self.embedding(decoder_inputs)  # Входные данные декодера
        decoder_lstm_output, _, _ = self.decoder_lstm(decoder_embedded, initial_state=[hidden_state, cell_state])
        output = self.dense(decoder_lstm_output)  # Shape (batch_size, 15, sql_vocab_size)

        return output

# **Создаем входные данные для декодера**
decoder_input_data = np.pad(X[:, :-1], ((0, 0), (1, 0)), mode="constant")  # Сдвигаем на 1 шаг влево

# **Инициализация модели**
model = SQLGenerator(text_vocab_size, sql_vocab_size)
model.compile(optimizer="adam", loss="categorical_crossentropy", metrics=["accuracy"])

# **Обучение**
model.fit([X, decoder_input_data], y, epochs=50, batch_size=32)

# **Сохранение**
model.save("sql_generator.h5")
with open("text_tokenizer.pkl", "wb") as f:
    pickle.dump(text_tokenizer, f)
with open("sql_tokenizer.pkl", "wb") as f:
    pickle.dump(sql_tokenizer, f)

# **Тестирование модели**
def generate_sql(text):
    sequence = text_tokenizer.texts_to_sequences([text])
    sequence = keras.preprocessing.sequence.pad_sequences(sequence, maxlen=15)
    decoder_input = np.zeros((1, 15))
    output = model.predict([sequence, decoder_input])
    predicted_sequence = np.argmax(output, axis=-1)[0]
    predicted_sql = sql_tokenizer.sequences_to_texts([predicted_sequence])[0]
    return predicted_sql

# **Запуск теста**
if __name__ == "__main__":
    while True:
        user_input = input("Введите текстовый запрос (или 'exit' для выхода): ")
        if user_input.lower() == "exit":
            break
        print("Сгенерированный SQL:", generate_sql(user_input))