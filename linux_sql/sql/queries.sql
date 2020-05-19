-- Question 1
--Group hosts by CPU number and sort by their memory size in descending order(within each cpu_number group)
SELECT cpu_number,id,total_mem
FROM host_info
GROUP BY cpu_number
ORDER BY total_mem DESC;

-- Question 2
-- Average memeory usage
SELECT id, hostname,
	time_char( to_timestamp(round(extract(epoch from host_usage.timestamp) / 300) * 300), 'YYYY-MM-DD HH24:MI:SS') AS time_run,
	(AVG(total_mem-memory_free)*100/total_mem)::INTEGER AS memory_percentage 
FROM host_info 
INNER JOIN  host_usage ON host_info.id=host_usage.host_id 
GROUP BY time_run, id ASC;
