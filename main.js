const { app, BrowserWindow, ipcMain } = require('electron');
const path = require('path');
const fs = require('fs');

const createWindow = () => {
  const win = new BrowserWindow({
    width: 1100,
    height: 700,
  });

  win.loadFile('main/renderer/views/index.html');
};

ipcMain.handle('does-account-exist', async (event, username) => {
  // Aqui você deve verificar se o nome de usuário existe, seja consultando um banco de dados ou arquivo
  const filePath = path.join(__dirname, 'accounts.json'); // Exemplo de caminho de arquivo
  let accounts = [];
  
  if (fs.existsSync(filePath)) {
      accounts = JSON.parse(fs.readFileSync(filePath, 'utf8'));
  }

  // Verifica se o nome de usuário já existe
  return accounts.some(account => account.username === username);
});

// Criar a conta
ipcMain.handle('create-account', async (event, username) => {
  const filePath = path.join(__dirname, 'accounts.json');
  let accounts = [];
  
  if (fs.existsSync(filePath)) {
      accounts = JSON.parse(fs.readFileSync(filePath, 'utf8'));
  }

  // Verifica se o nome de usuário já existe
  if (accounts.some(account => account.username === username)) {
      return { success: false, message: 'Este nome de usuário já existe.' };
  } else {
      // Cria a nova conta e salva
      accounts.push({ username: username });
      fs.writeFileSync(filePath, JSON.stringify(accounts, null, 2));
      return { success: true, message: 'Conta criada com sucesso!' };
  }
});

app.whenReady().then(() => {
  createWindow()
});

app.on('window-all-closed', () => {
  if (process.platform !== 'darwin') {
    app.quit();
  }
});
