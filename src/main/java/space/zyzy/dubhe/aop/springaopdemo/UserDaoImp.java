package space.zyzy.dubhe.aop.springaopdemo;

import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImp implements UserDao {

    @Override
    public int addUser() {
        System.out.println("add user ......");
        return 1;
    }

    @Override
    public void updateUser() {
        System.out.println("update user ......");
    }

    @Override
    public void deleteUser() {
        System.out.println("delete user ......");
    }

    @Override
    public void findUser() {
        System.out.println("find user ......");
    }
}