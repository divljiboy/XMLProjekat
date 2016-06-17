using System;
using System.Diagnostics;
using System.IO;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web;
using System.Web.Http;
using System.Web.UI.WebControls;

namespace IstorijskaArhivaNSa.Controllers
{
    [RoutePrefix("archive")]
    public class ArchiveController : ApiController
    {

        //[Route("save")]
        //public Task<HttpResponseMessage> Save()
        //{
        //    Console.WriteLine("POGODJEN");
        //    if (!Request.Content.IsMimeMultipartContent())
        //    {
        //        throw new HttpResponseException(HttpStatusCode.UnsupportedMediaType);
        //    }

        //    var root = HttpContext.Current.Server.MapPath("~/App_Data");
        //    var provider = new MultipartFormDataStreamProvider(root);

        //    var task = Request.Content.ReadAsMultipartAsync(provider).
        //        ContinueWith<HttpResponseMessage>(t =>
        //        {
        //            if (t.IsFaulted || t.IsCanceled)
        //            {
        //                Console.WriteLine("aaaaa");
        //                Request.CreateErrorResponse(HttpStatusCode.InternalServerError, t.Exception);
        //            }

        //            foreach (var file in provider.FileData)
        //            {
        //                Console.WriteLine("USAOOOO:");
        //                Console.WriteLine(file.Headers.ContentDisposition.FileName);
        //                Console.WriteLine(file.LocalFileName);
        //                //Trace.WriteLine(file.Headers.ContentDisposition.FileName);
        //                //Trace.WriteLine("Server file path: " + file.LocalFileName);
        //            }
        //            return Request.CreateResponse(HttpStatusCode.OK);
        //        });

        //    return task;
        //}

        [Route("save")]
        public HttpResponseMessage Save()
        {
            try
            {
                var file = HttpContext.Current.Request.Files.Count > 0 ?
                    HttpContext.Current.Request.Files[0] : null;

                var path = HttpContext.Current.Server.MapPath("~/Zip");

                byte[] fileData = null;
                using (var binaryReader = new BinaryReader(file.InputStream))
                {
                    fileData = binaryReader.ReadBytes(file.ContentLength);
                }

                File.WriteAllBytes($"{path}\\{file.FileName}", fileData);

                return new HttpResponseMessage(HttpStatusCode.OK);
            }
            catch{
                return new HttpResponseMessage(HttpStatusCode.BadRequest);
            }
        }


        [Route("get")]
        public HttpResponseMessage GetArchive()
        {
            return new HttpResponseMessage(HttpStatusCode.OK);
        }


    }
}
