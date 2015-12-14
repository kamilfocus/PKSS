__author__ = 'rafal'

from logs import Logger
import random


class EnergyProvider:

    is_broken = False

    @staticmethod
    def get_water_temperature(previous_temperature):
        """
        Oblicza temperaturę wody, na podstawie temperatury otoczenia 8h temu.
        :param previous_temperature: Temperatura otoczenia 8h temu.
        :return: Temperatura wody.
        """

        # Jeżeli nie jest zepsute, wylosuj szanse na zepsucie
        
        if not EnergyProvider.is_broken:
            i = random.randint(1, 100)
            if i == 1:
                EnergyProvider.is_broken = True
        else:
            i = random.randint(1, 50)
            if i == 1:
                EnergyProvider.is_broken = False

        if EnergyProvider.is_broken:
            Logger.error("AWARIA AWARIA AWARIA AWARIA AWARIA AWARIA ")

        if not EnergyProvider.is_broken:
            val = 70 - 2.5*(previous_temperature-6)
            val = 70 if val < 70 else val
            val = 135 if val > 135 else val
        else:
            val = previous_temperature

        return val