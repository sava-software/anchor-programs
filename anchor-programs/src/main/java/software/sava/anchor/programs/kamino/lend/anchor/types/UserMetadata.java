package software.sava.anchor.programs.kamino.lend.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

// Referrer account -> each owner can have multiple accounts for specific reserves
public record UserMetadata(PublicKey _address,
                           Discriminator discriminator,
                           // Pubkey of the referrer/owner - pubkey::default if no referrer
                           PublicKey referrer,
                           // Bump used for validation of account address
                           long bump,
                           // User lookup table - used to store all user accounts - atas for each reserve mint, each obligation PDA, UserMetadata itself and all referrer_token_states if there is a referrer
                           PublicKey userLookupTable,
                           // User metadata account owner
                           PublicKey owner,
                           long[] padding1,
                           long[] padding2) implements Borsh {

  public static final int BYTES = 1032;
  public static final int PADDING_1_LEN = 51;
  public static final int PADDING_2_LEN = 64;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int REFERRER_OFFSET = 8;
  public static final int BUMP_OFFSET = 40;
  public static final int USER_LOOKUP_TABLE_OFFSET = 48;
  public static final int OWNER_OFFSET = 80;
  public static final int PADDING_1_OFFSET = 112;
  public static final int PADDING_2_OFFSET = 520;

  public static Filter createReferrerFilter(final PublicKey referrer) {
    return Filter.createMemCompFilter(REFERRER_OFFSET, referrer);
  }

  public static Filter createBumpFilter(final long bump) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, bump);
    return Filter.createMemCompFilter(BUMP_OFFSET, _data);
  }

  public static Filter createUserLookupTableFilter(final PublicKey userLookupTable) {
    return Filter.createMemCompFilter(USER_LOOKUP_TABLE_OFFSET, userLookupTable);
  }

  public static Filter createOwnerFilter(final PublicKey owner) {
    return Filter.createMemCompFilter(OWNER_OFFSET, owner);
  }

  public static UserMetadata read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static UserMetadata read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static UserMetadata read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], UserMetadata> FACTORY = UserMetadata::read;

  public static UserMetadata read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var referrer = readPubKey(_data, i);
    i += 32;
    final var bump = getInt64LE(_data, i);
    i += 8;
    final var userLookupTable = readPubKey(_data, i);
    i += 32;
    final var owner = readPubKey(_data, i);
    i += 32;
    final var padding1 = new long[51];
    i += Borsh.readArray(padding1, _data, i);
    final var padding2 = new long[64];
    Borsh.readArray(padding2, _data, i);
    return new UserMetadata(_address,
                            discriminator,
                            referrer,
                            bump,
                            userLookupTable,
                            owner,
                            padding1,
                            padding2);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    referrer.write(_data, i);
    i += 32;
    putInt64LE(_data, i, bump);
    i += 8;
    userLookupTable.write(_data, i);
    i += 32;
    owner.write(_data, i);
    i += 32;
    i += Borsh.writeArray(padding1, _data, i);
    i += Borsh.writeArray(padding2, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
