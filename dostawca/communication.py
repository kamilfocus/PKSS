__author__ = 'rafal'


from json_builder import jsonBuilder as JsonBuilder
from config import Configuration
from logs import Logger

import socket
import time
import os


class Communication(object):

    TIME_KEY = 'trzy_miliony'

    def __init__(self, model):
        Logger.info("Created communication")

        self.host = Configuration.get_hostname()
        self.port = Configuration.get_port()
        self.socket = None
        self.model = model
        self.communication_tries = 20
        self.time = 10

        self.role = Configuration.get_role()
        self.json_builder = JsonBuilder(self.role)

        self.prepare_connection()

    def __del__(self):
        if self.socket is not None:
            self.socket.close()
            Logger.info("Killing socket on exit.")

    def set_model(self, model):
        self.model = model

    def prepare_connection(self):
        Logger.info("Initializing communication")
        Logger.info("Creating connection to %s:%s" % (self.host, self.port))
        self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        if self.socket is None:
            Logger.error("Failed to create socket.")

        for t in range(self.communication_tries):
            try:
                self.socket.connect((self.host, self.port))
                Logger.info("Successfully connected to host.")
                break
            except (ConnectionRefusedError, socket.timeout) as e:
                Logger.error("Failed to connect to host. %s" % e)
            time.sleep(0.5)
        else:
            self.socket = None

    def run(self):
        if self.socket is None:
            Logger.error("Socket does not exists, aborting!")
            return

        self.__send_init()
        js = self.__receive_init()

        try:
            self.time = js[Communication.TIME_KEY]
        except:
            Logger.info('Using default value of time ' + str(self.time))
            self.time = 10
        while True:
          self.__cycle0()

    def __cycle0(self):
        # self.time += 1
        self.model.tick(self.time)
        self.__send_data()
        self.__receive_data()
        Logger.info("Tick complete")
        time.sleep(1)
        os.system("clear")


    def __send_init(self):
        self.json_builder.switch_to_init_json(self.role)
        self.__send(self.json_builder.serialize())

    def __send_data(self):
        self.json_builder.switch_to_data_json(self.role)
        for k, v in self.model.get_state().items():
            self.json_builder.add_field(k, v)

        self.__send(self.json_builder.serialize())

    def __send(self, msg):
        Logger.debug('Sending msg=%s to server.' % msg)
        if self.socket is None:
            Logger.warning("Will not send message, socket does not exist.")
            return

        for t in range(self.communication_tries):
            try:
                Logger.info('Trying to send msg.')
                self.socket.sendall(bytes(msg, 'utf-8'))
                Logger.debug('Msg sent.')
                break
            except Exception as e:
                Logger.warning('Exception caught. ' + str(e))
                time.sleep(0.5)
        else:
            Logger.error("Failed to send message.")

    def __receive(self, size=1024):
        if self.socket is None:
            Logger.warning("Will not receive message, socket does not exist.")
            return None
        self.socket.setblocking(True)
        msg = self.socket.recv(size)
        #Logger.debug('Received bytes ' + str(msg))
        self.socket.setblocking(False)
        return msg

    def __receive_time(self):
        js = self.json_builder.deserialize(self.__receive())
        self.time = js['time']
        Logger.info("Received time value %f" % self.time)

    def __receive_init(self):
        js = self.json_builder.deserialize(self.__receive())
        Logger.info('Init msg received. ' + str(js))
        return js

    def __receive_data(self):
        js = self.json_builder.deserialize(self.__receive())
        # Logger.debug('Received msg: ' + str(js))
        try:
            js.pop('type')
        except:
            Logger.warning("No key type in json.")
        if Communication.TIME_KEY in js.keys():
            self.time = js[Communication.TIME_KEY]
        Logger.debug(str(js))
