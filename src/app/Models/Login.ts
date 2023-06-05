export class Login 
{
    private username: string;
    private password: string;

    constructor(username: string, password: string){
        this.username = username;
        this.password = password;
    }

    getUsername(): string
    {
        return this.username;
    }
    
    setUsername(username: string): Login
    {
        this.username = username;

        return this;
    }

    getPassword(): string
    {
        return this.password;
    }

    setPassword(password: string): Login
    {
        this.password = password;

        return this;
    }
}