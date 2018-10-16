request = function()
   db_sleep = string.format("%.2f", math.random())
   srv_sleep = string.format("%.2f", math.random())
   is_db_error = tostring(math.random() < 0.7 and 0 or 1)
   is_srv_error = tostring(math.random() < 0.8 and 0 or 1)
   -- sleep
   -- is_error

   path = "/complex?db_sleep=" .. db_sleep .. "&srv_sleep=" .. srv_sleep
   path = path .. "&is_db_error=" .. is_db_error .. "&is_srv_error=" .. is_srv_error

   wrk.method = "GET"
   print(path)

   return wrk.format(nil, path)
end
