-- Question 1
--Group hosts by CPU number and sort by their memory size in descending order(within each cpu_number group)
SELECT id,cpu_number,total_mem
FROM host_info
GROUP BY cpu_number,id 
ORDER BY total_mem DESC;

-- Question 2
-- Average memeory usage
SELECT host_usage.host_id, host_info.hostname, host_usage.timestamp, 
    AVG(((host_info.total_mem - host_usage.memory_free) / host_info.total_mem::float) * 100) 
      OVER ( 
        PARTITION BY (
            DATE_TRUNC('hour', host_usage.timestamp) + 
            DATE_PART('min', host_usage.timestamp)::int / 5 * interval '5 min'
        )
    ) AS avg_used_mem_perc 
FROM 
    host_usage  INNER JOIN host_info  ON host_usage.host_id = host_info.id
ORDER BY
    host_usage.host_id; 
