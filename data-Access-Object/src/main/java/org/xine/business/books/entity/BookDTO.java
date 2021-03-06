package org.xine.business.books.entity;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * A Transfer Object doesn’t require major rethinking, except the fact that it became superfuous for the majority of all use cases.
 * JPA entities do a great job transferring data between layers, especially in a single JVM.
 * A TO use to be common as a general best practice in J2EE to hide CMP-specifc details, which is no longer necessary.
 * @author jcosta
 */
public class BookDTO implements Externalizable {

    private static final long serialVersionUID = 1L;

    private int numberOfPages;
    private String name;

    public BookDTO(final int numberOfPages, final String name) {
        this.numberOfPages = numberOfPages;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getNumberOfPages() {
        return this.numberOfPages;
    }

    public void setNumberOfPages(final int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    @Override
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeUTF(this.name);
        out.writeInt(this.numberOfPages);
    }

    @Override
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        this.name = in.readUTF();
        this.numberOfPages = in.readInt();
    }

}
