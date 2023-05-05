local key = KEYS[1]
local del_key = KEYS[2]
local max_value = tonumber(KEYS[3])
local expire_time = tonumber(KEYS[4])

if redis.call("EXISTS", key) == 0 then -- 如果 key 不存在，创建新 key，删除指定 key，返回 true
    redis.call("SET", key, 1)
    redis.call("EXPIRE", key, expire_time)
    redis.call("DEL", del_key)
    return true
else -- 如果 key 存在，判断 value 是否大于 max_value，返回 true 或 false
    local value = tonumber(redis.call("GET", key))
    if value > max_value then
        return false
    else
        redis.call("INCR", key)
        return true
    end
end