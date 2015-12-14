#!/usr/bin/env python3
__author__ = "Rafał Prusak"
__doc__ = "skrypt uruchamiający symylację dostawcy energi"

from communication import Communication
from wheather import WheatherGenerator
from config import Configuration
from model import Model
from logs import Logger
import time
import json

if __name__ == "__main__":

    Logger.init()

    with open('data.txt') as data_file:
        WheatherGenerator.data = json.load(data_file)
    # print(WheatherGenerator.data.keys())

    Configuration.init()
    provider_model = Model()
    communicator = Communication(provider_model)

    while 1:
        # time.sleep(2)
        communicator.run()
