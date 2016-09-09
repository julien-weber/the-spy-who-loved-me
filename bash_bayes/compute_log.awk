BEGIN {
    mode = "NULL"
    p_hire = 0
    p_reject = 0
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

        p_hire += log(accept_count) - log(accept_count + reject_count)
        p_reject += log(reject_count) - log(accept_count + reject_count)
    } else {
        print "ERROR: received no mode directive, exiting."
        exit 1
    }
}
END {
    max_log = p_hire
    if (p_reject > max_log) {
        max_log = p_reject
    }

    p_hire = exp(p_hire - max_log)
    p_reject = exp(p_reject - max_log)

    norm = p_hire + p_reject
    p_hire = p_hire / norm
    p_reject = p_reject / norm

    print "P[hire] = ", p_hire
    print "P[reject] = ", p_reject
}
