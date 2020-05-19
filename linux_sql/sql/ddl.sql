-- Create table host info
CREATE TABLE IF NOT EXISTS host_info
  (id SERIAL NOT NULL, 
  hostname VARCHAR NOT NULL,
  cpu_number INT NOT NULL,
  cpu_architecture VARCHAR NOT NULL, 
  cpu_model VARCHAR NOT NULL, cpu_mhz DECIMAL NOT NULL, 
  L2_cache INT NOT NULL, 
  total_mem INT NOT NULL, 
  timestamp TIMESTAMP NOT NULL, 
  PRIMARY KEY (id));
               
-- Create table host usage 
CREATE TABLE IF NOT EXISTS host_usage
 (timestamp TIMESTAMP NOT NULL,
 host_id SERIAL NOT NULL REFERENCES host_info(id),
 memory_free INT NOT NULL,
 cpu_idle INT NOT NULL,
 cpu_kernel INT NOT NULL,
 disk_io INT NOT NULL,
 disk_available INT NOT NULL);
