import { test, expect, Page } from '@playwright/test';

// Example of Page Object Model
class LoginPage {
  constructor(private page: Page) {}

  async navigate() {
    await this.page.goto('https://demo.playwright.dev/todomvc');
  }

  async addTodo(todoText: string) {
    await this.page.getByPlaceholder('What needs to be done?').fill(todoText);
    await this.page.getByPlaceholder('What needs to be done?').press('Enter');
  }

  async getTodoCount() {
    return await this.page.getByTestId('todo-item').count();
  }

  async toggleTodo(index: number) {
    await this.page.getByTestId('todo-item').nth(index).getByRole('checkbox').check();
  }

  async deleteTodo(index: number) {
    await this.page.getByTestId('todo-item').nth(index).hover();
    await this.page.getByTestId('todo-item').nth(index).getByLabel('Delete').click();
  }
}

test.describe('Todo App Tests', () => {
  let todoPage: LoginPage;

  test.beforeEach(async ({ page }) => {
    todoPage = new LoginPage(page);
    await todoPage.navigate();
  });

  test('should allow adding a new todo', async ({ page }) => {
    await todoPage.addTodo('Learn Playwright');
    
    await expect(page.getByTestId('todo-item')).toHaveText('Learn Playwright');
  });

  test('should allow marking todo as completed', async ({ page }) => {
    await todoPage.addTodo('Complete automation tests');
    await todoPage.toggleTodo(0);
    
    await expect(page.getByTestId('todo-item')).toHaveClass(/completed/);
  });

  test('should allow deleting a todo', async ({ page }) => {
    await todoPage.addTodo('Delete this todo');
    await todoPage.deleteTodo(0);
    
    await expect(page.getByTestId('todo-item')).toHaveCount(0);
  });

  test('should persist todos after page refresh', async ({ page }) => {
    await todoPage.addTodo('Persistent todo');
    await page.reload();
    
    await expect(page.getByTestId('todo-item')).toHaveText('Persistent todo');
  });
});