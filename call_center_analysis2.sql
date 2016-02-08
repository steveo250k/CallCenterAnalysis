-- Number of calls with a real customer.
SELECT COUNT(*)
FROM ft_call_record cr
JOIN d_customer c ON c.d_customer_key = cr.d_customer_key
WHERE c.customer_id <> '0'
;
-- Calls with no customer ID.
SELECT *
FROM ft_call_record cr
JOIN d_customer c ON c.d_customer_key = cr.d_customer_key
WHERE c.customer_id = '0'
;
-- Number of calls by customer. 
SELECT c.customer_id, count(*) AS total_calls
FROM ft_call_record cr
JOIN d_customer c ON c.d_customer_key = cr.d_customer_key
GROUP BY c.customer_id
ORDER BY total_calls desc
;
-- Number of calls by call type.
SELECT CONCAT(ct.call_type, '-', ct.call_type_description) AS call_type, count(*) AS total_calls
FROM ft_call_record cr
JOIN d_call_type ct ON ct.d_call_type_key = cr.d_call_type_key
GROUP BY call_type
ORDER BY total_calls desc
;
-- Number of calls by day in January
SELECT dd.calendar_date, dd.day_of_week, COUNT(cr.ft_call_record_key) AS total_calls
FROM d_date dd
JOIN ft_call_record cr ON dd.d_date_key = cr.call_date_dimension_key
WHERE dd.month_number_in_year = 1
GROUP BY dd.calendar_date, dd.day_of_week
ORDER BY dd.calendar_date
;
--Call volume by day for Jan 1999
SELECT dd.calendar_date, dd.day_of_week, COUNT(cr.ft_call_record_key) AS total_calls
FROM d_date dd
LEFT JOIN ft_call_record cr ON dd.d_date_key = cr.call_date_dimension_key
WHERE dd.year_ = 1999
AND   dd.month_number_in_year = 1
GROUP BY dd.calendar_date, dd.day_of_week
ORDER BY dd.calendar_date
;
--Call volume by day for Dec 1999
SELECT dd.calendar_date, dd.day_of_week, COUNT(cr.ft_call_record_key) AS total_calls
FROM d_date dd
LEFT JOIN ft_call_record cr ON dd.d_date_key = cr.call_date_dimension_key
WHERE dd.year_ = 1999
AND   dd.month_number_in_year = 9
GROUP BY dd.calendar_date, dd.day_of_week
ORDER BY dd.calendar_date
;
;
