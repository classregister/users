import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method DELETE()
        url '/users/1'
    }
    response {
        status 204
    }
}
