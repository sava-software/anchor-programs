package software.sava.anchor.programs.kamino.vaults.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

public record ReserveWhitelistEntry(PublicKey _address,
                                    Discriminator discriminator,
                                    // The token mint is stored to solve the problem of finding all the whitelisted reserves for a particular token mint:
                                    // when storing the token mint inside the PDA, finding all the whitelisted reserves becomes a `getProgramAccounts` with
                                    // a filter on discriminator + the mint field
                                    // The reserve pubkey, as seed of the reserve whitelist PDA account, it stored so you can link back the PDA to its seeds
                                    // (for instance, in the operation above we easily find the reserve corresponding to the PDA)
                                    PublicKey tokenMint,
                                    PublicKey reserve,
                                    int whitelistAddAllocation,
                                    int whitelistInvest,
                                    byte[] padding) implements Borsh {

  public static final int BYTES = 136;
  public static final int PADDING_LEN = 62;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int TOKEN_MINT_OFFSET = 8;
  public static final int RESERVE_OFFSET = 40;
  public static final int WHITELIST_ADD_ALLOCATION_OFFSET = 72;
  public static final int WHITELIST_INVEST_OFFSET = 73;
  public static final int PADDING_OFFSET = 74;

  public static Filter createTokenMintFilter(final PublicKey tokenMint) {
    return Filter.createMemCompFilter(TOKEN_MINT_OFFSET, tokenMint);
  }

  public static Filter createReserveFilter(final PublicKey reserve) {
    return Filter.createMemCompFilter(RESERVE_OFFSET, reserve);
  }

  public static Filter createWhitelistAddAllocationFilter(final int whitelistAddAllocation) {
    return Filter.createMemCompFilter(WHITELIST_ADD_ALLOCATION_OFFSET, new byte[]{(byte) whitelistAddAllocation});
  }

  public static Filter createWhitelistInvestFilter(final int whitelistInvest) {
    return Filter.createMemCompFilter(WHITELIST_INVEST_OFFSET, new byte[]{(byte) whitelistInvest});
  }

  public static ReserveWhitelistEntry read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static ReserveWhitelistEntry read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static ReserveWhitelistEntry read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], ReserveWhitelistEntry> FACTORY = ReserveWhitelistEntry::read;

  public static ReserveWhitelistEntry read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var tokenMint = readPubKey(_data, i);
    i += 32;
    final var reserve = readPubKey(_data, i);
    i += 32;
    final var whitelistAddAllocation = _data[i] & 0xFF;
    ++i;
    final var whitelistInvest = _data[i] & 0xFF;
    ++i;
    final var padding = new byte[62];
    Borsh.readArray(padding, _data, i);
    return new ReserveWhitelistEntry(_address,
                                     discriminator,
                                     tokenMint,
                                     reserve,
                                     whitelistAddAllocation,
                                     whitelistInvest,
                                     padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    tokenMint.write(_data, i);
    i += 32;
    reserve.write(_data, i);
    i += 32;
    _data[i] = (byte) whitelistAddAllocation;
    ++i;
    _data[i] = (byte) whitelistInvest;
    ++i;
    i += Borsh.writeArrayChecked(padding, 62, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
