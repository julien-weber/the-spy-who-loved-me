BEGIN {
    mode = "NULL"
    p_hire = 1.0
    p_reject = 1.0
}
/<ACCEPT>/ { mode = "ACCEPT" }
/<REJECT>/ { mode = "REJECT" }
/<INPUT>/  { mode = "INPUT" }
{
    if (mode == "ACCEPT") {
        accept[$2] = int($1)
    } else if (mode == "REJECT") {
        reject[$2] = int($1)
    } else if (mode == "INPUT") {
        accept_count = 1.0
        if ($0 in accept) {
            accept_count += accept[$0]
        }

        reject_count = 1.0
        if ($0 in reject) {
            reject_count += reject[$0]
        }

        p_hire *= accept_count / (accept_count + reject_count)
        p_reject *= reject_count / (accept_count + reject_count)
    } else {
        print "ERROR: received no mode directive, exiting."
        exit 1
    }
}
END {
    norm = p_hire + p_reject
    p_hire = p_hire / norm
    p_reject = p_reject / norm

    print "P[hire] = ", p_hire
    print "P[reject] = ", p_reject
}
