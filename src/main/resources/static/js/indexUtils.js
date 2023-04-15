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