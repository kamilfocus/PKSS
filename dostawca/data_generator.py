#!/usr/bin/env python3
__author__ = "Rafał Prusak"
__doc__ = "Skrypt zapisujący informację o pogodzie"

import json
from config import Configuration
from wheather import WheatherGenerator
from datetime import datetime, timedelta
from dateutil import parser

if __name__ == '__main__':

    result = {}

    Configuration.init()
    start_time = Configuration.get_start_time()
    time = parser.parse(start_time)
    time = time - timedelta(hours=8)
    i = 1
    while i <= 10000:
        try:
            temperature = WheatherGenerator.get_temperature(time)
            result[str(time)] = temperature
            print(i, temperature, str(time))
            time = time + timedelta(minutes=10)
            i += 1
        except:
            print("koniec kluczy api!")
            break

    with open('data.txt', 'w') as outfile:
        json.dump(result, outfile)