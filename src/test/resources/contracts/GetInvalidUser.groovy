import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method GET()
        url '/users/2'
    }
    response {
        status 404
        body(
                [
                        type  : value(consumer('/users/2'), producer(regex('.+'))),
                        title : value(consumer('User does not exist'), producer(regex('.+'))),
                        status: value(consumer('NOT_FOUND'), producer(regex('.+'))),
                        detail: value(consumer('User with id: 2 not found'), producer(regex('.+')))
                ]
        )
        headers {
            contentType(applicationJsonUtf8())
        }
    }
}

