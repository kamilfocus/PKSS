__author__ = 'rafal'

from client import Client
from wheather import WheatherGenerator
from energy_provider import EnergyProvider
from config import Configuration
from logs import Logger


class Model:

    def __init__(self):
        Configuration.init()
        self.client = Client(Configuration.get_start_time())
        self.state = None

    def tick(self, time):
        self.client.update_time(time)
        params = self.__calculate_parameters()
        self.__log_parameters(params)
        current_time, current_temperature, previous_temperature, water_temperature = params
        self.state = {"T_zm": water_temperature, "T_o": current_temperature}
        return params

    def get_state(self):
        return self.state

    def __calculate_parameters(self):
        """
        Wylicza parametry dostawcy energi.
        """

        current_time = self.client.get_current_time()    # aktualny czas
        previous_time = self.client.get_previous_time()  # czas 8h temu
        current_temperature = WheatherGenerator.get_temperature(current_time)    # temperatura otoczenia
        previous_temperature = WheatherGenerator.get_temperature(previous_time)  # temperatura otoczenia 8h temu
        water_temperature = EnergyProvider.get_water_temperature(previous_temperature)  # temperatura wody

        parameters = (current_time, current_temperature, previous_temperature, water_temperature)
        return parameters

    def __log_parameters(self, params):
        """
        Zapisuje parametry do logów.
        """

        current_time, current_temp, previous_temp, water_temp = params
        current_time = "time="+str(current_time)
        current_temp = "current_temp="+str(current_temp)+" st. C"
        previous_temp = "previous_temp="+str(previous_temp)+" st. C"
        water_temp = "water_temp="+str(water_temp)+" st. C"
        msg = current_time+" "+current_temp+" "+previous_temp+" "+water_temp
        Logger.info(msg)


if __name__ == '__main__':
    import time
    Logger.init()
    Configuration.init()
    provider_model = Model()

    while 1:
        print(provider_model.tick(10))