import org.springframework.cloud.contract.spec.Contract

Contract.make {
	request {
		method PUT()
		url '/users/1'
		body(
				[
						login   : value(consumer(regex('.+')), producer('user_login')),
						password: value(consumer(regex('.+')), producer('user_password'))
				]
		)
		headers {
			contentType(applicationJsonUtf8())
		}
	}
	response {
		status 200
		body(
				[
						id      : value(consumer('1'), producer(regex('[0-9]+'))),
						login   : value(consumer('user'), producer(regex('.+'))),
						password: value(consumer('some_password'), producer(regex('.+')))
				]
		)
		headers {
			contentType(applicationJsonUtf8())
		}
	}
}
