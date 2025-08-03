import FeedbackListPage from "./FeedbackListPage";
import UsersTable from "../components/UserList";

function AdminPage() {
    return (
        <div>
            <h1>Admin Dashboard</h1>

            <section>
                <FeedbackListPage />
            </section>
            <hr />
            <section>
                <UsersTable />
            </section>
        </div>
    );
}
export default AdminPage;