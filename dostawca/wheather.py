__author__ = 'rafal'


import random
from logs import Logger


class WheatherGenerator:

    data = None
    last_temperature = 0

    @staticmethod
    def get_temperature(time):

        key_time = str(time)

        if '.' in key_time:
            key_time = key_time.split(sep='.')[0]

        if key_time in WheatherGenerator.data.keys():
            temperature = WheatherGenerator.data[key_time]
            WheatherGenerator.last_temperature = temperature
        else:
            Logger.error("Blad kluczy")
            Logger.error(key_time)
            temperature = WheatherGenerator.last_temperature + random.randint(-3, 3)
            WheatherGenerator.last_temperature = temperature

        return temperature