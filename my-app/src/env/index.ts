// передаю URL-адресу віддаленого сервера в змінну REMOTE_HOST_NAME
const REMOTE_HOST_NAME: string = process.env.REACT_APP_BASE_URL as string;
//експортую цю змінну в об'єкт APP_ENV щоб інші частини програми могли отримати доступ до цієї змінної.
const APP_ENV = {
    REMOTE_HOST_NAME: REMOTE_HOST_NAME
};
export { APP_ENV };