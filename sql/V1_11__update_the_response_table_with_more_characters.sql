DROP VIEW db_schema.view_request_with_response;

ALTER TABLE db_schema.response
ALTER COLUMN text TYPE VARCHAR(5000);

ALTER TABLE db_schema.request
ALTER COLUMN text TYPE VARCHAR(5000);

CREATE VIEW db_schema.view_request_with_response AS
SELECT req.id_request,
	req.text as text_request,
	req.date as date_request,
	req.id_audio_mongo as id_audio,
	req.id_user,
	req.id_context,
	res.text as text_response,
	res.date as date_response
	FROM db_schema.request as req
	LEFT JOIN db_schema.response as res
	ON req.id_request = res.id_request
	ORDER BY date_request DESC;