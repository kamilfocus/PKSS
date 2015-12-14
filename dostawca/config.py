__author__ = 'rafal'

import json
from datetime import datetime


class Configuration:

    __config = {}

    @staticmethod
    def restore_default():
        cfg = {}
        cfg["last_date"] = str(datetime.now())
        cfg["latitude"] = "50.06143"
        cfg["longitude"] = "19.93658"
        # TODO tutaj może się wyjebać
        cfg["start_time"] = "2014-01-02 06:00:00.000000"
        cfg["hostname"] = "127.0.0.1"
        cfg["port"] = 1234
        cfg["role"] = "dostawca"
        Configuration.__config = cfg
        Configuration.__save()

    @staticmethod
    def __load():
        with open('config.json') as config_file:
            Configuration.__config = json.load(config_file)

    @staticmethod
    def __save():
        with open('config.json', 'w') as config_file:
            json.dump(Configuration.__config, config_file)

    @staticmethod
    def init():
        Configuration.__load()
        today = datetime.now()
        Configuration.__config["last_date"] = str(today)
        Configuration.__save()

    @staticmethod
    def get_latitude():
        return Configuration.__config["latitude"]

    @staticmethod
    def get_longitude():
        return Configuration.__config["longitude"]

    @staticmethod
    def get_hostname():
        return Configuration.__config["hostname"]

    @staticmethod
    def get_port():
        return Configuration.__config["port"]

    @staticmethod
    def get_role():
        return Configuration.__config["role"]

    @staticmethod
    def get_start_time():
        return Configuration.__config["start_time"]

if __name__ == '__main__':
    Configuration.restore_default()

