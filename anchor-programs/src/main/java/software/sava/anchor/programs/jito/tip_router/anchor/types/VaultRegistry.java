package software.sava.anchor.programs.jito.tip_router.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

public record VaultRegistry(PublicKey _address,
                            Discriminator discriminator,
                            PublicKey ncn,
                            int bump,
                            byte[] reserved,
                            StMintEntry[] stMintList,
                            VaultEntry[] vaultList) implements Borsh {

  public static final int BYTES = 27368;
  public static final int RESERVED_LEN = 127;
  public static final int ST_MINT_LIST_LEN = 64;
  public static final int VAULT_LIST_LEN = 64;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int NCN_OFFSET = 8;
  public static final int BUMP_OFFSET = 40;
  public static final int RESERVED_OFFSET = 41;
  public static final int ST_MINT_LIST_OFFSET = 168;
  public static final int VAULT_LIST_OFFSET = 14056;

  public static Filter createNcnFilter(final PublicKey ncn) {
    return Filter.createMemCompFilter(NCN_OFFSET, ncn);
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static VaultRegistry read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static VaultRegistry read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static VaultRegistry read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], VaultRegistry> FACTORY = VaultRegistry::read;

  public static VaultRegistry read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var ncn = readPubKey(_data, i);
    i += 32;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var reserved = new byte[127];
    i += Borsh.readArray(reserved, _data, i);
    final var stMintList = new StMintEntry[64];
    i += Borsh.readArray(stMintList, StMintEntry::read, _data, i);
    final var vaultList = new VaultEntry[64];
    Borsh.readArray(vaultList, VaultEntry::read, _data, i);
    return new VaultRegistry(_address,
                             discriminator,
                             ncn,
                             bump,
                             reserved,
                             stMintList,
                             vaultList);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    ncn.write(_data, i);
    i += 32;
    _data[i] = (byte) bump;
    ++i;
    i += Borsh.writeArray(reserved, _data, i);
    i += Borsh.writeArray(stMintList, _data, i);
    i += Borsh.writeArray(vaultList, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
