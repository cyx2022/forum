local key1 = KEYS[1]                  -- 获取第一个key
local key2 = KEYS[2]                  -- 获取第二个key

local val = tonumber(redis.call("GET", key1)) -- 获取key1的值并转换为数字类型

if val and val > 0 then               -- 如果值存在且大于0
    val = val - 1                     -- 执行减1操作
    redis.call("SET", key1, val)      -- 更新key1的值

    if val == 0 then                  -- 如果减完1后值为0
        redis.call("SET", key2, 1)    -- 新增另一个key，值为1
        redis.call("EXPIRE", key2, 86400) -- 过期时间为一天
        return true                   -- 返回true
    else
        return true                  -- 返回false
    end
else
    return false                      -- 返回false
end