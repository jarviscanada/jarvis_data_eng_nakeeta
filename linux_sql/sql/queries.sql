-- Question 1
--Group hosts by CPU number and sort by their memory size in descending order(within each cpu_number group)
SELECT cpu_number,id,total_mem
FROM host_info
GROUP BY cpu_number
ORDER BY total_mem DESC;

-- Question 2
-- Average memeory usage
SELECT host_info.id,host_info.hostname,
        -- find Average used memory in percentage over 5 mins interval
      avg_used_mem_percentage(
        AVG(host_info.total_mem - host_usage.memory_free)*100/host_info.total_mem)
          OVER(
            PARTITION BY (
              hour('hour', host_usage.timestamp)+ minute('min',host_info.timestamp) :: int 5
            )
          )
      )
FROM host_info, host_usage
INNER JOIN host_usage on host_info.id=host_usage.id
ORDER by host_usage.id 
