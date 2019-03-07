var Home = React.createClass({
  render() {
    return (
		<div>
            <h3>Welcome to the Library DBMS!</h3>
            <ul className="header">
              <li><a href="/search">Search</a></li>
              <li><a href="/checkin">CheckIN</a></li>
              <li><a href="/borrower">Create Borrower</a></li>
              <li><a href="/fines">Calculate Fines</a></li>
            </ul>
            <div className="content">
            </div>
        </div>
    );
  }
});

ReactDOM.render(
  <Home/>, 
  document.getElementById("root")
);