import sys

def main():
    f = open(sys.argv[1])
    total_wt = 0
    for line in f.readlines():
        weight = int(line.split()[2])
        total_wt += weight

    print("Total weight:", total_wt)

main()
