import subprocess
import threading
from flask import Flask, request, jsonify
import requests
import configparser

configs = configparser.ConfigParser()
configs.read('service.properties')

MICROSERVICE_PORT = configs.get('DEFAULT', 'MICROSERVICE_PORT')
RASA_PORT = configs.get('DEFAULT', 'RASA_PORT')
HOST_ADDRESS = configs.get('DEFAULT', 'HOST_ADDRESS')
CONSUME_ROUTE = configs.get('DEFAULT', 'CONSUME_ROUTE')
MODEL_PATH = configs.get('DEFAULT', 'MODEL_PATH')
TEXT_KEY = 'text'
RASA_URL = f"http://localhost:{RASA_PORT}/model/parse"

class RasaPayload:
    def __init__(self, text):
        self.text = text

    def to_dict(self):
        return {TEXT_KEY: self.text}

class RasaResponse:
    def __init__(self, data):
        self.data = data

    @classmethod
    def from_response(cls, response):
        return cls(response.json())

    def to_dict(self):
        return self.data

class RasaRunner:
    def __init__(self, model_path, port=3333):
        self.model_path = model_path
        self.port = port

    def start_rasa_server(self):
        rasa_command = [
            "rasa",
            "run",
            "--enable-api",
            "--port",
            str(self.port),
            "-m",
            self.model_path
        ]
        try:
            subprocess.run(rasa_command, check=True)
        except subprocess.CalledProcessError as e:
            print("An error occurred:", e)

class RestClient:
    def __init__(self, app):
        self.app = app

    def initialize_flask_app(self):
        self.app.route(CONSUME_ROUTE, methods=['POST'])(self.consume_rasa)

    def consume_rasa(self):
        try:
            request_data = request.json
            input_text = request_data.get(TEXT_KEY, '')

            if not input_text:
                return jsonify({"error": "Text not provided."}), 400

            payload = RasaPayload(text=input_text)
            response = requests.post(RASA_URL, json=payload.to_dict())

            if response.status_code == 200:
                parsed_data = RasaResponse.from_response(response).to_dict()
                return jsonify(parsed_data), 200
            else:
                return jsonify({"error": "An error occurred while parsing the text."}), response.status_code

        except Exception as e:
            return jsonify({"error": "An error occurred.", "details": str(e)}), 500

class RasaService:
    def __init__(self, rasa_port, microservice_port):
        self.rasa_port = rasa_port
        self.microservice_port = microservice_port
        self.rasa_runner = RasaRunner(model_path=MODEL_PATH, port=rasa_port)

    def start_services(self):
        rasa_thread = threading.Thread(target=self.rasa_runner.start_rasa_server)
        rasa_thread.start()

        app = Flask(__name__)
        rest_client = RestClient(app)
        rest_client.initialize_flask_app()

        app.run(host=HOST_ADDRESS, port=self.microservice_port)

if __name__ == '__main__':
    microservice = RasaService(rasa_port=RASA_PORT, microservice_port=MICROSERVICE_PORT)
    microservice.start_services()
