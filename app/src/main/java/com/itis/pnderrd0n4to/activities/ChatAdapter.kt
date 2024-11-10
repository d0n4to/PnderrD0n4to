import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itis.pnderrd0n4to.R
import com.itis.pnderrd0n4to.activities.Message

class ChatAdapter(private val messages: List<Message>) : RecyclerView.Adapter<ChatAdapter.MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.bind(message)
    }

    override fun getItemCount() = messages.size

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val contentTextView: TextView = itemView.findViewById(R.id.user1)
        private val contentTextView2: TextView = itemView.findViewById(R.id.user2)

        fun bind(message: Message) {
            if (message.sender == "user1") {
                contentTextView.text = "USER1: ${message.content}"
                contentTextView.visibility = View.VISIBLE
                contentTextView2.visibility = View.GONE
            } else {
                contentTextView2.text = "USER2: ${message.content}"
                contentTextView2.visibility = View.VISIBLE
                contentTextView.visibility = View.GONE
            }
        }
    }
}
