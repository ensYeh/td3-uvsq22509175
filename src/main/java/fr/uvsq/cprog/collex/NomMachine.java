package fr.uvsq.cprog.collex;

import java.util.Objects;

public final class NomMachine {
    private final String fullName;
    private final String host;
    private final String domain;

    public NomMachine(String name) {
        if (name == null) throw new IllegalArgumentException("Nom ne peut pas être null");
        String s = name.trim();
        int idx = s.indexOf('.');
        if (idx <= 0 || idx == s.length() - 1) {
            throw new IllegalArgumentException("Format de nom de machine invalide: " + name);
        }
        this.fullName = s;
        this.host = s.substring(0, idx);
        this.domain = s.substring(idx + 1);
    }

    public String getHost() {
        return host;
    }

    public String getDomain() {
        return domain;
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NomMachine)) return false;
        NomMachine that = (NomMachine) o;
        return Objects.equals(fullName, that.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName);
    }
}
