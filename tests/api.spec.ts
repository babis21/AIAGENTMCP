import { test, expect } from '@playwright/test';

test.describe('API Testing Examples', () => {
  test('should test API endpoints', async ({ request }) => {
    // Test GET request
    const response = await request.get('https://jsonplaceholder.typicode.com/posts/1');
    expect(response.status()).toBe(200);
    
    const post = await response.json();
    expect(post).toHaveProperty('id', 1);
    expect(post).toHaveProperty('title');
    expect(post).toHaveProperty('body');
  });

  test('should test POST request', async ({ request }) => {
    const newPost = {
      title: 'Test Post',
      body: 'This is a test post created by Playwright',
      userId: 1
    };

    const response = await request.post('https://jsonplaceholder.typicode.com/posts', {
      data: newPost
    });
    
    expect(response.status()).toBe(201);
    
    const createdPost = await response.json();
    expect(createdPost).toHaveProperty('id');
    expect(createdPost.title).toBe(newPost.title);
  });

  test('should test PUT request', async ({ request }) => {
    const updatedPost = {
      id: 1,
      title: 'Updated Test Post',
      body: 'This post has been updated',
      userId: 1
    };

    const response = await request.put('https://jsonplaceholder.typicode.com/posts/1', {
      data: updatedPost
    });
    
    expect(response.status()).toBe(200);
    
    const post = await response.json();
    expect(post.title).toBe(updatedPost.title);
  });

  test('should test DELETE request', async ({ request }) => {
    const response = await request.delete('https://jsonplaceholder.typicode.com/posts/1');
    expect(response.status()).toBe(200);
  });
});