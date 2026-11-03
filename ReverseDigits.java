Algorithm ReverseDigits(N):
    Input: N (integer)
    Output: reversed integer

    rev ← 0
    while N > 0 do
        digit ← N mod 10
        rev ← rev * 10 + digit
        N ← N div 10
    print rev
