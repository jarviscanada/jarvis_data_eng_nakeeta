-- Question 1
--Group hosts by CPU number and sort by their memory size in descending order(within each cpu_number group)
SELECT cpu_number,id,total_mem
FROM host_info
GROUP BY cpu_number
ORDER BY total_mem DESC;

-- Question 2
-- Average memeory usage
SELECT host_usage.id,host_info.hostname, host_usage.timestamp,
        -- find Average used memory in percentage over 5 mins interval
        AVG((host_info.total_mem - host_usage.memory_free)*100/host_info.total_mem) 
        OVER ( 
            PARTITION BY (
                hour('hour', host_usage.timestamp) + 
                min('min', host_usage.timestamp)::int / 5 * interval '5 min'
            )
        ) AS avg_used_mem_perc
        
FROM host_info, host_usage
INNER JOIN host_usage on host_info.id=host_usage.id
ORDER by host_usage.id ;
