Algorithm PrimeCheck(N):
    Input: N (integer > 1)
    Output: "Prime" or "Not Prime"

    if N <= 1 then
        print "Not Prime"
    else
        flag ← true
        for i ← 2 to sqrt(N) do
            if N mod i = 0 then
                flag ← false
                break
        if flag = true then
            print "Prime"
        else
            print "Not Prime"
