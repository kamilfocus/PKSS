#!/usr/bin/env python3

import socket

from datetime import datetime, timedelta
from dateutil import parser


class Client:

    def __init__(self, start_time=datetime.now(), update_step=10):
        self.__time = parser.parse(start_time)
        self.__time_step = update_step
        self.__previous_time = self.__time - timedelta(hours=8)

    def update_time(self, udpate_time):
        """
        Aktualizuje czas przechowywany przez klienta.
        """

        ''' To działało
        self.__time = self.__time + timedelta(minutes=10)
        self.__previous_time = self.__time - timedelta(hours=8)
        '''

        udpate_time = (udpate_time//10)*10

        self.__time = self.__time + timedelta(minutes=udpate_time)
        self.__previous_time = self.__time - timedelta(hours=8)


    def get_current_time(self):
        """
        Pobiera aktualny czas.
        """

        return self.__time

    def get_previous_time(self):
        """
        Pobiera czas 8 godzin temu.
        """

        return self.__previous_time
