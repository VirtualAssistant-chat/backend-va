# Setting Up a Python Virtual Environment

A virtual environment is a self-contained directory that contains a specific Python interpreter and all the packages required for your project. Using a virtual environment helps you isolate your project's dependencies and prevents conflicts with other Python projects. Here's how to create and activate a virtual environment in Python:

## Prerequisites

Before you begin, make sure you have Python installed on your system. You can check this by running:

```bash
python --version
python3 -m venv myenv
```

##Activating the Virtual Environment
To activate the virtual environment, use the following command:
```bash
source myenv/bin/activate
```
Your terminal prompt should change to indicate that the virtual environment is active. This means that any Python packages you install will be isolated within this environment.
##installing necessary packages
```bash
pip install rasa
pip install flask
pip install tensorflow==2.12.0
```

##Running microservice
```bash
python3 RasaService.py
```
