package fr.uvsq.cprog.collex;

import java.util.Objects;

public final class AdresseIP implements Comparable<AdresseIP> {
    private final int[] octets;

    public AdresseIP(String ip) {
        if (ip == null) {
            throw new IllegalArgumentException("IP ne peut pas être null");
        }
        String s = ip.trim();
        String[] parts = s.split("\\.");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Format d'IP invalide: " + ip);
        }
        octets = new int[4];
        for (int i = 0; i < 4; i++) {
            try {
                int v = Integer.parseInt(parts[i]);
                if (v < 0 || v > 255) {
                    throw new IllegalArgumentException("Octet hors limites: " + parts[i]);
                }
                octets[i] = v;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Octet invalide: " + parts[i]);
            }
        }
    }

    public int[] getOctets() {
        return new int[] {octets[0], octets[1], octets[2], octets[3]};
    }

    @Override
    public String toString() {
        return octets[0] + "." + octets[1] + "." + octets[2] + "." + octets[3];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdresseIP)) return false;
        AdresseIP adresseIP = (AdresseIP) o;
        for (int i = 0; i < 4; i++) if (octets[i] != adresseIP.octets[i]) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(octets[0], octets[1], octets[2], octets[3]);
    }

    @Override
    public int compareTo(AdresseIP other) {
        for (int i = 0; i < 4; i++) {
            int c = Integer.compare(this.octets[i], other.octets[i]);
            if (c != 0) return c;
        }
        return 0;
    }
}
