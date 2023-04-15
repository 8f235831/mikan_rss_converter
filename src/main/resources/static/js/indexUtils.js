export function verifyFollowItem(item) {
	if (item == null) {
		throw new Error('item == null');
	}
	const id = item.id;
	const rssSite = item.rssSite;
	const regexFilter = item.regexFilter;
	const comment = item.comment;
	const enabled = item.enabled;
	const followAddedTime = item.followAddedTime;
	const followModifiedTime = item.followModifiedTime;
	const lastUpdateSucceedTime = item.lastUpdateSucceedTime;
	const updateContinueFailCounter = item.updateContinueFailCounter;

	if (typeof id !== 'number' || !(id >= 0)) {
		throw new Error('id is not a correct number: ' + id);
	}
	if (typeof rssSite !== 'string') {
		throw new Error('rssSite is not a string: ' + rssSite);
	}
	if (typeof regexFilter !== 'string') {
		throw new Error('regexFilter is not a string: ' + regexFilter);
	}
	if (typeof comment !== 'string') {
		throw new Error('comment is not a string: ' + comment);
	}
	if (typeof enabled !== 'number' || !(enabled === 0 || enabled === 1)) {
		throw new Error('enabled is not a number in {0, 1}: ' + enabled);
	}
	if (typeof followAddedTime !== 'number' || !(followAddedTime >= 0)) {
		throw new Error('followAddedTime is not a correct number: ' + followAddedTime);
	}
	if (typeof followModifiedTime !== 'number' || !(followModifiedTime >= 0)) {
		throw new Error('followModifiedTime is not a correct number: ' + followModifiedTime);
	}
	if (typeof lastUpdateSucceedTime !== 'number' || !(lastUpdateSucceedTime >= 0)) {
		throw new Error('lastUpdateSucceedTime is not a correct number: ' + lastUpdateSucceedTime);
	}
	if (typeof updateContinueFailCounter !== 'number' || !(updateContinueFailCounter >= 0)) {
		throw new Error('updateContinueFailCounter is not a correct number: ' + updateContinueFailCounter);
	}
}

export function verifyFollowList(list) {
	if (list == null) {
		throw new Error('list == null');
	}
	for (const index in list) {
		verifyFollowItem(list[index]);
	}
}

export function formatDate(unixTimestamp) {
	console.log('unixTimestamp:' + unixTimestamp)
	if (typeof unixTimestamp !== 'number' || unixTimestamp <= 0) {
		return 'N/A';
	}
	const date = new Date(unixTimestamp * 1000);
	const y = date.getFullYear();
	let m = date.getMonth() + 1;
	m = m < 10 ? ('0' + m) : m;
	let d = date.getDate();
	d = d < 10 ? ('0' + d) : d;
	let h = date.getHours();
	h = h < 10 ? ('0' + h) : h;
	let minute = date.getMinutes();
	minute = minute < 10 ? ('0' + minute) : minute;
	let second = date.getSeconds();
	second = second < 10 ? ('0' + second) : second;
	return y + '-' + m + '-' + d + ' ' + h + ':' + minute + ':' + second;
}