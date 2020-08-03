<!doctype html>
<html lang="en">
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

        <title>Student Attendance Monitoring System</title>
    </head>
    <body>

        <div class="container">
            <div class="row d-flex justify-content-center align-items-center" style="height:600px;">
                <div class="col-lg-7">
                    <div class="card" >
                        <div class="card-header">
                            <h3>Student Attendance Monitoring System</h3>
                        </div>
                        <div class="card-body">
                            <h5 class="card-title">Signin to Your Account</h5>
                            <form method="post" action="main">
                        <input type="hidden" name="action" value="authenticate"/>
                        <div class="form-group">
                            <label class="form-control-label">User ID:</label>
                            <input type="text" name="username" class="form-control" placeholder="Enter your user ID">
                        </div>
                        <!-- form-group -->

                        <div class="form-group">
                            <label class="form-control-label">Password:</label>
                            <input type="password" name="password" class="form-control" placeholder="Enter your password">
                        </div>
                        <!-- form-group -->
                        <br/>
                        <div class="text-center">
                            <input type="submit" class="btn btn-primary" value="Sign In">
                        </div>
                    </form>
                        </div>
                    </div>


                    
                </div>
            </div>
            <!-- col-7 -->
        </div>
        <!-- Optional JavaScript -->
        <!-- jQuery first, then Popper.js, then Bootstrap JS -->
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    </body>
</html>