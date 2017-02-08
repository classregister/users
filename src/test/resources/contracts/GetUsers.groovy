import org.springframework.cloud.contract.spec.Contract

Contract.make {
	request {
		method GET()
		urlPath('/users') {
			queryParameters {
				parameter 'page' : value(regex('[0-9]+'))
				parameter 'size' : value(regex('[0-9]+'))
			}
		}
	}
	response {
		status 200
		body(
				content: [
						[
								id      : value(consumer('1'), producer(regex('[0-9]+'))),
								login   : value(consumer('user'), producer(regex('.+'))),
								password: value(consumer('some_password'), producer(regex('.+')))
						]
				],
				last: value(consumer(false), producer(regex('true|false'))),
				first: value(consumer(true), producer(regex('true|false'))),
				totalPages: value(consumer(1), producer(regex('[0-9]+'))),
				totalElements: value(consumer(1), producer(regex('[0-9]+'))),
				numberOfElements: value(consumer(1), producer(regex('[0-9]+'))),
				number: value(consumer(1), producer(regex('[0-9]+'))),
				size: value(consumer(1), producer(regex('[0-9]+')))
		)
		headers {
			contentType(applicationJsonUtf8())
		}
	}
}
