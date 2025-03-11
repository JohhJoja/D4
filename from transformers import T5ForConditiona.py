import numpy as np
import tensorflow as tf
from tensorflow import keras
from tensorflow.keras.preprocessing.text import Tokenizer
from tensorflow.keras.preprocessing.sequence import pad_sequences
from tensorflow.keras.models import Model
from tensorflow.keras.layers import Input, Embedding, LSTM, Dense, Bidirectional, Dropout
from tensorflow.keras.callbacks import EarlyStopping

# Текстовые данные и SQL
data = [
    {"text": "Найди, пожалуйста, заявку с кодом IM453779 и статусом ожидает в группе А для пользователя Артем Захарович Антонов", "sql": "SELECT * FROM tickets WHERE code = 'IM453779' AND status = 'ожидает' AND groupS = 'А' AND last_name = 'Антонов' AND first_name = 'Артем' AND middle_name = 'Захарович'"},
    # Добавь все остальные строки из твоего датасета
]

# Разделим тексты на текст и sql
texts = [entry["text"] for entry in data]
sqls = [entry["sql"] for entry in data]

# Токенизация
tokenizer = Tokenizer(oov_token="<OOV>")
tokenizer.fit_on_texts(texts + sqls)

# Преобразуем тексты и SQL в последовательности
text_sequences = tokenizer.texts_to_sequences(texts)
sql_sequences = tokenizer.texts_to_sequences(sqls)

# Приводим длину последовательностей к одной длине (например, maxlen = 30)
maxlen = 30
X = pad_sequences(text_sequences, maxlen=maxlen)
y = pad_sequences(sql_sequences, maxlen=maxlen)

# Подготовим модель
vocab_size = len(tokenizer.word_index) + 1  # Учтем OOV токен

# Энкодер
encoder_inputs = Input(shape=(maxlen,))
x = Embedding(vocab_size, 128)(encoder_inputs)
x = Bidirectional(LSTM(64, return_sequences=True))(x)
x = Dropout(0.5)(x)

# Декодер
decoder_lstm = LSTM(64, return_sequences=True)(x)
decoder_dense = Dense(vocab_size, activation='softmax')(decoder_lstm)

# Модель
model = Model(encoder_inputs, decoder_dense)

# Компиляция модели
model.compile(optimizer='adam', loss='sparse_categorical_crossentropy', metrics=['accuracy'])

# Добавим callback для ранней остановки
early_stopping = EarlyStopping(monitor='val_loss', patience=3)

# Обучение модели
model.fit(X, np.expand_dims(y, -1), epochs=50, batch_size=32, validation_split=0.2, callbacks=[early_stopping])

# Сохраним модель
model.save('sql_query_model.h5')
