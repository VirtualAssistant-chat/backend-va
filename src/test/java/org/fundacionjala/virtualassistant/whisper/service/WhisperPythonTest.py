import asyncio
import unittest
from unittest.mock import Mock
from unittest.mock import patch
from fastapi import HTTPException
import sys

sys.path.append("src/main/java/org/fundacionjala/virtualassistant/whisper/service/")
from ASRClient import AudioValidator, FileHandler, Transcriber, enumException


class TestAudioValidator(unittest.TestCase):
    def test_validate_audio_file_valid(self):
        audio_file_mock = Mock()
        is_valid, error = AudioValidator.validate_audio_file(audio_file_mock)

        self.assertTrue(is_valid)
        self.assertIsNone(error)

    def test_validate_audio_file_not_found(self):
        audio_file_mock = Mock()
        audio_file_mock.filename = ""
        audio_file_mock.content_type = "audio/wav"

        with self.assertRaises(HTTPException) as context:
            AudioValidator.validate_audio_file(audio_file_mock)

        self.assertEqual(context.exception.detail, enumException.not_found)

    def test_validate_audio_file_not_valid(self):
        audio_file_mock = Mock()
        audio_file_mock.filename = "src/test/resources/10sec.wav"
        audio_file_mock.content_type = ""

        with self.assertRaises(HTTPException) as context:
            AudioValidator.validate_audio_file(audio_file_mock)

        self.assertEqual(context.exception.detail, enumException.not_valid)

    @patch("ASRClient.Transcriber.transcribe")
    def test_validate_transcribe(self, mock_transcribe):
        mock_transcribe.return_value = "test"
        self.assertEqual(Transcriber.transcribe("tiny", "test"), "test")

    @patch("ASRClient.FileHandler.delete_audio")
    def test_validate_delete_audio(self, mock_delete_audio):
        mock_delete_audio.return_value = None
        self.assertIsNone(FileHandler.delete_audio("test"))

    @patch("ASRClient.convert_to_text")
    def test_validate_convert_to_text(self, mock_convert_to_text):
        mock_convert_to_text.return_value = "Simulated audio data"
        self.assertEqual(
            asyncio.run(mock_convert_to_text("test.wav")), "Simulated audio data"
        )


if __name__ == "__main__":
    unittest.main()
