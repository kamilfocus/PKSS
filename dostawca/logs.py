__author__ = 'rafal'

import logging


class Logger:

    @staticmethod
    def init():
        format_style = '%(asctime)s %(levelname)s %(filename)s: %(message)s'
        logging.basicConfig(format=format_style, level=logging.DEBUG)

    @staticmethod
    def info(txt):
        logging.info(txt)

    @staticmethod
    def error(txt):
        logging.error(txt)

    @staticmethod
    def warning(txt):
        logging.warning(txt)

    @staticmethod
    def debug(txt):
        logging.debug(txt)
