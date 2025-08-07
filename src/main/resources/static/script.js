const API = '/api/bookmarks';
const form = document.getElementById('bookmarkForm');
const bookmarksDiv = document.getElementById('bookmarks');

form.addEventListener('submit', async (e) => {
  e.preventDefault();
  const data = {
    title: form.title.value,
    url: form.url.value,
    tags: form.tags.value,
    description: form.description.value,
  };
  try {
    await fetch(API, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data)
    });
    form.reset();
    loadBookmarks();
  } catch (err) {
    alert('Failed to add bookmark');
  }
});

async function loadBookmarks() {
  const res = await fetch(API);
  const bookmarks = await res.json();
  bookmarksDiv.innerHTML = '';
  bookmarks.forEach(b => {
    const div = document.createElement('div');
    div.className = 'bookmark';
    div.innerHTML = `
      <a href="${b.url}" target="_blank">${b.title}</a>
      <div>${b.description || ''}</div>
      <small>Tags: ${b.tags || '-'}</small>
      <div class="actions">
        <button onclick="deleteBookmark(${b.id})">❌</button>
      </div>
    `;
    bookmarksDiv.appendChild(div);
  });
}

async function deleteBookmark(id) {
  if (!confirm('Delete this bookmark?')) return;
  await fetch(`${API}/${id}`, { method: 'DELETE' });
  loadBookmarks();
}

loadBookmarks();
