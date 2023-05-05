local key = KEYS[1]
local exists = redis.call('EXISTS', key)

if exists == 1 then
    redis.call('DEL', key)
    return true
else
    return false
end
