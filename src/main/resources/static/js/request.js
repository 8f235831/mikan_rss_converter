import "./lib/axios.js";

export function getFull(
	path,
	params,
	onSuccess,
	onWebFailure,
	onResponseFailure,
	verify,
	onValidateFailure,
	afterAll
) {
	axios.get(path, {params: params})
		.then(response => {
				// handle server error
				if (response.status !== 200) {
					if (onWebFailure != null) {
						onWebFailure(new Error('response.status == ' + response.status))
					}
					return;
				}
				// unpack data
				let code = response.data.code;
				let msg = response.data.msg;
				let body = response.data.body;
				if (code === undefined) {
					if (onValidateFailure != null) {
						onValidateFailure(new Error('Code in response is undefined!'), body);
					}
					return;
				}
				if (msg === undefined) {
					msg = null;
				}
				if (body === undefined) {
					body = null;
				}
				if (code >= 0) {
					if (verify != null) {
						try {
							verify(body);
						} catch (e) {
							if (onValidateFailure != null) {
								onValidateFailure(e);
							}
							return;
						}
					}
					onSuccess(code, msg, body);
				} else {
					if (
						onResponseFailure != null) {
						onResponseFailure(new Error('Receive failure code[' + code + ']!'), code, msg);
					}
				}
			}
		)
		.catch(function (error) {
			if (onWebFailure != null) {
				onWebFailure(error);
			}
		})
		.finally(function () {
				if (afterAll != null) {
					afterAll();
				}
			}
		);
}
